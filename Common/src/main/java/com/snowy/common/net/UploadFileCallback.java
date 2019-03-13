package com.snowy.common.net;


import com.snowy.common.entity.DataPart;

import java.util.List;

interface UploadFileCallback {

	void onFailure();

	void onResponse(List<DataPart> dataList);


}
