package com.snowy.ttword;

import static com.snowy.common.BaseConstants.ROOT_PATH;

/**
 * @author guobaolun
 * @since 2019/3/13
 */


public interface Constants {


    public static final String SP_KEY_THEME_MODE = "ThemeMode";
    public static final String SP_KEY_PHONETIC_LIST_VOICE = "PhoneticListVoice";



    String RESOURCES_AUDIO_PATH = ROOT_PATH + "audio/";
    String RESOURCES_PHONETIC_PATH = RESOURCES_AUDIO_PATH + "phonetic/";
    String RESOURCES_UK_WORD_PATH = RESOURCES_AUDIO_PATH + "AudioResources/uk/";
    String RESOURCES_US_WORD_PATH = RESOURCES_AUDIO_PATH + "AudioResources/us/";


}
