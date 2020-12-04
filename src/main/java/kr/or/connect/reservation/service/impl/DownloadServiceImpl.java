package kr.or.connect.reservation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.reservation.dao.DownloadDao;
import kr.or.connect.reservation.dto.DownloadDto;
import kr.or.connect.reservation.service.DownloadService;

@Service
public class DownloadServiceImpl implements DownloadService {
	@Autowired
	DownloadDao downloadDao;

	@Override
	public DownloadDto getDownloadFileInfo(int fileInfoId) {
		DownloadDto fileData = downloadDao.getDownloadFileInfo(fileInfoId);
		return fileData;
	}
	

	
	
}
