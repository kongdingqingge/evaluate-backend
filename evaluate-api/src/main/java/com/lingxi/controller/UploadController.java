package com.lingxi.controller;

import com.lingxi.enums.AppHttpCodeEnum;
import com.lingxi.model.param.DeletePic;
import com.lingxi.model.param.DeletePics;
import com.lingxi.model.po.GUmpires;
import com.lingxi.result.ResponseResult;
import com.lingxi.service.GUmpiresService;
import com.lingxi.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 图片上传控制器
 * </p>
 *
 * @author tan
 */
@Slf4j
@RestController
@RequestMapping("/pic")
public class UploadController {


    @Autowired
    private UploadService uploadService;

    @Autowired
    private GUmpiresService gUmpiresService;


    /**
     * 2.图片批量删除
     */
    @PostMapping("/deleteList")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult deletepics(@RequestBody DeletePics deleteAPName) {
        Boolean success = uploadService.deleteList(deleteAPName);
        if (success) {
            return ResponseResult.okResult(200, "删除成功");
        }
        return ResponseResult.errorResult(400, "删除失败");
    }

    /**
     * 1.图片上传
     */
    @PreAuthorize("@TAN.hasAuthority('admin')")
    @PostMapping("/upload/{id}")
    public ResponseResult uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Integer id) {
        GUmpires gUmpires = gUmpiresService.getById(id);
        try {
            byte[] fileBytes = file.getBytes();
            String fileName = file.getOriginalFilename();
            String imageUrl = uploadService.uploadPic(fileBytes, fileName);
            if (imageUrl != null) {
                gUmpires.setAvatar(imageUrl);
                boolean success = gUmpiresService.updateById(gUmpires);
                if (!success) {
                    return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
                }
                return ResponseResult.okResult(imageUrl); // 返回图片的访问地址
            } else {
                return ResponseResult.errorResult(400, "上传失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.errorResult(400, "上传失败");
        }
    }

    /**
     * 3.文件删除
     */
    @PostMapping("/delete")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult delete(@RequestBody DeletePic deletepic) {
        Boolean success = uploadService.deletePic(deletepic);
        if (success) {
            return ResponseResult.okResult(200, "删除成功");
        }
        return ResponseResult.errorResult(400, "删除失败");
    }


}
