package com.lingxi.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.lingxi.enums.AppHttpCodeEnum;
import com.lingxi.exception.CustomException;
import com.lingxi.mapper.GMatchesMapper;
import com.lingxi.model.dto.ImportMatchDto;
import com.lingxi.model.dto.ImportPlayGroundDto;
import com.lingxi.model.dto.ImportProjectsDto;
import com.lingxi.model.dto.ImportUnitDto;
import com.lingxi.model.po.GMatches;
import com.lingxi.model.vo.GunitVo;
import com.lingxi.model.vo.MatchVo;
import com.lingxi.service.GMatchesService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static com.google.common.io.Files.getFileExtension;

/**
 * <p>
 * 赛程表 服务实现类
 * </p>
 *
 * @author tan
 */
@Service
public class GMatchesServiceImpl extends ServiceImpl<GMatchesMapper, GMatches> implements GMatchesService {
    @Autowired
    private GMatchesMapper matchesMapper;


    /**
     * 导入项目,上传裁判图片
     *
     * @return
     */


    //-------------------------------------------------------------------------
    //1.获取单元格中的字符串值
    private static String getStringCellValue(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return "";
        }
        return cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : "";
    }

    //2.获取单元格中的整数值
    private static Integer getIntCellValue(Cell cell) {
        return cell.getCellType() == CellType.NUMERIC ? (int) cell.getNumericCellValue() : 0;
    }

    //3.获取单元格中的日期时间值
    private static LocalDateTime getDateTimeCellValue(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            // 如果是数值类型，将其作为日期时间值解析
            return cell.getLocalDateTimeCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            // 如果是文本类型，将其作为字符串解析后再转换为日期时间值
            String dateTimeStr = cell.getStringCellValue();
            return LocalDateTime.parse(dateTimeStr);
        } else {
            // 其他类型，返回null或者根据实际需求进行处理
            return null;
        }
    }

    //4.获取单元格里面的图片
    public static String getImageBytesAndNameFromCell(Cell cell) {
        Drawing<?> drawing = cell.getSheet().createDrawingPatriarch();
        if (drawing instanceof XSSFDrawing) {
            XSSFDrawing xssfDrawing = (XSSFDrawing) drawing;
            for (XSSFShape shape : xssfDrawing.getShapes()) {
                if (shape instanceof XSSFPicture) {
                    XSSFPicture picture = (XSSFPicture) shape;
                    XSSFClientAnchor anchor = picture.getClientAnchor();
                    if (anchor != null && anchor.getCol1() == cell.getColumnIndex() && anchor.getRow1() == cell.getRowIndex()) {
                        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                            PackagePart packagePart = picture.getPictureData().getPackagePart();
                            String imageName = packagePart.getPartName().getName();
                            String substring = imageName.substring(imageName.lastIndexOf('/') + 1);
                            InputStream inputStream = packagePart.getInputStream();
                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                baos.write(buffer, 0, bytesRead);
                            }
                            String uploadPic = uploadPic(baos.toByteArray(), substring);
                            return uploadPic;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }


    //5.裁判图片上传
    public static String uploadPic(byte[] bytes, String fileName) {
        // 1.检查文件大小是否超过限制
        long fileSizeInBytes = bytes.length;
        long maxFileSizeInBytes = 10 * 1024 * 1024; // 10MB
        if (fileSizeInBytes > maxFileSizeInBytes) {
            throw new CustomException(AppHttpCodeEnum.FILE_DATE_MAX);
        }
        Configuration cfg = new Configuration(Region.huanan()); // 区域
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2; // 指定分片上传版本
        UploadManager uploadManager = new UploadManager(cfg);

        String substring = fileName.substring(0, 3);
        // 获取当前时间并格式化
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String timestamp = now.format(formatter);
        // 获取文件后缀
        String fileExtension = getFileExtension(fileName);
        String name = substring + "-" + UUID.randomUUID().toString().substring(0, 6) + "-" + timestamp + "." + fileExtension;

        String key = name; // 文件名
        Auth auth = Auth.create("YhKDmPq1wIhOTaDkvtAw28NiyUIlsODPoK-kKdeP", "Rmf6yuBjbVoanh5nv68RmNTL5Iz9-tkw_iHSxBHQ");
        String upToken = auth.uploadToken("GUmpires");

        try {
            Response response = uploadManager.put(bytes, key, upToken);
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            if (putRet != null && !putRet.key.isEmpty()) {
                String imageUrl = "http://rxztdtyqd.hn-bkt.clouddn.com/" + putRet.key;
                return imageUrl;
            } else {
                System.err.println("上传失败，未返回有效的图片地址！");
            }
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println("上传失败，错误信息：" + r.toString());
            try {
                System.err.println("上传失败，详细错误信息：" + r.bodyString());
            } catch (QiniuException ex2) {
                System.err.println("获取详细错误信息时发生异常：" + ex2.getMessage());
            }
        }
        return null;
    }

    //1.获取单元格中的字符串值
    private static String getStringCell(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return "";
        }
        return cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : "";
    }

    //2.获取单元格中的整数值
    private static Integer getIntCell(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return -1; // 返回一个特定值表示单元格为空
        }
        return cell.getCellType() == CellType.NUMERIC ? (int) cell.getNumericCellValue() : -1; // 返回一个特定值表示单元格不是数值类型
    }

    /**
     * 2.查询赛程所有相关数据
     *
     * @return
     */
    @Override
    public MatchVo getAllMatches() {
        return matchesMapper.getMatchAll();
    }

    /**
     * 删除赛程所有数据
     *
     * @param matchId
     * @return
     */
    @Override
    public boolean deleteMatch(Integer matchId) {
        return matchesMapper.deleteAll(matchId);
    }

    @Override
    public List<GunitVo> getUnitsByGId(Integer playGroundId) {
        return matchesMapper.getUnitAndProjects(playGroundId);
    }

    /**
     * 表格导入赛程
     *
     * @param file
     * @return
     */
    @Override
    public ImportMatchDto importMatch(MultipartFile file) {
        ArrayList<ImportMatchDto> importMatchDtos = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() < 1) {
                    continue;
                }
                String matchName = getStringCellValue(row.getCell(0));//赛程名称
                Integer playGroundCount = getIntCellValue(row.getCell(1));//场地数量
                ImportMatchDto importMatchDto = new ImportMatchDto(matchName, playGroundCount);
                importMatchDto.setImportPlayGroundDto(new ArrayList<>());

                int unitRow = 1;//单元结束行
                int projectRow = 1;//项目结束行
                for (int i = 0; i < playGroundCount; i++) {
                    int playGroundRow = row.getRowNum() + i;//场地累加行
                    String playGroundName = getStringCellValue(sheet.getRow(playGroundRow).getCell(2));
                    Integer unitCount = getIntCellValue(sheet.getRow(playGroundRow).getCell(3));
                    ImportPlayGroundDto ImportPlayGround = new ImportPlayGroundDto(playGroundName, unitCount);
                    ImportPlayGround.setImportUnitDtoList(new ArrayList<>());

                    for (int j = 0; j < unitCount; j++) {
                        int unitRow1 = row.getRowNum() + i + j;
                        int unitRow2 = unitRow + j;
                        String unitName = getStringCellValue(sheet.getRow(i == 0 ? unitRow1 : unitRow2).getCell(4));
                        Integer projectCount = getIntCellValue(sheet.getRow(i == 0 ? unitRow1 : unitRow2).getCell(5));
                        ArrayList<ImportProjectsDto> importProjects = new ArrayList<>();

                        for (int k = 0; k < projectCount; k++) {
                            int projectRow1 = row.getRowNum() + i + j + k;
                            int projectRow2 = projectRow + k;
                            String projectName = getStringCellValue(sheet.getRow(j == 0 && i == 0 ? projectRow1 : projectRow2).getCell(6));
                            ImportProjectsDto projects = new ImportProjectsDto(projectName);
                            importProjects.add(projects);//1.添加项目
                        }
                        projectRow += projectCount; // 跟新项目结束行
                        ImportUnitDto importUnit = new ImportUnitDto(unitName, projectCount, importProjects);//2.单元添加项目集合
                        ImportPlayGround.getImportUnitDtoList().add(importUnit);//3.场地添加单元集合
                    }
                    unitRow += unitCount;// 跟新组结束行
                    importMatchDto.getImportPlayGroundDto().add(ImportPlayGround);//4.赛程添加场地集合
                }
                importMatchDtos.add(importMatchDto);
                if (row.getRowNum() <= projectRow) {
                    break;
                }
            }
            // 处理完毕后，关闭文件输入流
            file.getInputStream().close();
            // 最终处理完文件后，删除临时文件
//            File tempFile = ((DiskFileItem) file).getStoreLocation();
//            if (tempFile.exists()) {
//                tempFile.delete();
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return importMatchDtos.get(0);
    }

    /**
     * 删除数据库里除用户表的所有数据
     *
     * @author Cyou
     */
    public void clearAll() {
        matchesMapper.clearAll();
    }

    /**
     * 清空队伍表和队伍项目表的数据
     *
     * @author Cyou
     */
    public void clearTeam() {
        matchesMapper.clearTeam();
    }


    /**
     * 清空裁判表和裁判打分表的所有数据
     *
     * @author Cyou
     */
    public void clearUmpires() {
        matchesMapper.clearUmpires();
    }

}