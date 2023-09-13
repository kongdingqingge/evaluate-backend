package com.lingxi.service;

import com.lingxi.model.param.DeletePic;
import com.lingxi.model.param.DeletePics;

public interface UploadService {

    String uploadPic(byte[] bytes, String fileName);

    Boolean deleteList(DeletePics deletePicsp);

    Boolean deletePic(DeletePic fileName);
}
