package com.udemy.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UploadFileServiceImpl implements IUploadFileService {

	private static final Log LOG = LogFactory.getLog(UploadFileServiceImpl.class);

	@Override
	public Resource load(String filename) throws MalformedURLException {
		Path pathFoto = getPath(filename);
		LOG.info("pathFoto: " + pathFoto);
		Resource recursoResource = null;

		recursoResource = new UrlResource(pathFoto.toUri());
		if (!recursoResource.exists() || !recursoResource.isReadable()) {
			throw new RuntimeException("error no se puede cargar la imagen" + pathFoto.toString());
		}

		return recursoResource;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		String uniqueFileNameString = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path rootPath = getPath(uniqueFileNameString);

		LOG.info("rootpath" + rootPath);

		Files.copy(file.getInputStream(), rootPath);

		return uniqueFileNameString;
	}

	@Override
	public boolean delete(String filename) {
		Path rootPath = getPath(filename);
		File archivo = rootPath.toFile();
		if(archivo.exists() && archivo.canRead()) {
			if(archivo.delete()) {
				return true;
			}
		}
		return false;
	}

	public Path getPath(String filename) {
		return Paths.get("uploads").resolve(filename).toAbsolutePath();
	}

}
