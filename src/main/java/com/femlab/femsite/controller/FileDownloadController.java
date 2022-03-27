package com.femlab.femsite.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;



import com.femlab.femsite.repository.PostingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// "/download/ファイル名" にアクセスでファイルをダウンロードできる

@RestController
@RequestMapping("/download")
public class FileDownloadController {
	@Autowired
	PostingRepository postingRepo;

	private static final String FILE_PATH = "repository/seminer/";

	@RequestMapping("/{fileName:.+}")
	public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileName") String fileName) throws IOException {
		final String EXTERNAL_FILE_PATH = Paths.get(FILE_PATH + fileName).toAbsolutePath().toString();  // 絶対パスに変換
		File file = new File(EXTERNAL_FILE_PATH);

		if (file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(fileName);
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);
			response.addHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(file.getName(), StandardCharsets.UTF_8.name()));
			response.setContentLength((int) file.length());

			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}
}