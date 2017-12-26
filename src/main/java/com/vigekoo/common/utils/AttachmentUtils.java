/**
 * Copyright (c) 2015-2016, Michael Yang 杨福海 (fuhai999@gmail.com).
 *
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vigekoo.common.utils;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author oplus
 * @Description: TODO(attachment文件处理)
 * @date 2017-6-23 15:07
 */
public class AttachmentUtils {

	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 创建文件名
	 * @param suffix
	 * @return
	 */
	public static String newFileName(String suffix){
		String webRoot = FileUtils.getTempPath();

		String uuid = UUID.randomUUID().toString().replace("-", "");

		StringBuilder newFileName = new StringBuilder(webRoot).append(File.separator).append("attachment")
				.append(File.separator).append(dateFormat.format(new Date())).append(File.separator).append(uuid)
				.append(suffix);

		File newfile = new File(newFileName.toString());

		if (!newfile.getParentFile().exists()) {
			newfile.getParentFile().mkdirs();
		}

		return newFileName.toString();
	}

	static List<String> imageSuffix = new ArrayList<String>();

	static {
		imageSuffix.add(".jpg");
		imageSuffix.add(".jpeg");
		imageSuffix.add(".png");
		imageSuffix.add(".bmp");
		imageSuffix.add(".gif");
	}

	public static boolean isImage(String path) {
		String sufffix = FileUtils.getSuffix(path);
		if (StringUtils.isNotBlank(sufffix))
			return imageSuffix.contains(sufffix.toLowerCase());
		return false;
	}

}
