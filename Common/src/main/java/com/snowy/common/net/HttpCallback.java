package com.snowy.common.net;

import java.io.IOException;


/**
 * @author guobaolun
 */
public interface HttpCallback {

	void onResponse(String body);

	void onFailure(IOException e) ;


}
