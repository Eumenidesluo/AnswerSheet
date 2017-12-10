package me.eumenides.service;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * Created by Eumenides on 2017/10/19.
 */
public interface AnswerSheetService extends Library {
    AnswerSheetService INSTANCE= Native.loadLibrary("AnswerSheetDll",AnswerSheetService.class);
    Pointer getAnswer(String fileName);
}
