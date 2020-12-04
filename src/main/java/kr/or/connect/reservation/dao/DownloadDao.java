package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.ReservationDaoSqls.*;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.DownloadDto;


@Repository
public class DownloadDao {
	 private NamedParameterJdbcTemplate jdbc;
	    private RowMapper<DownloadDto> downloadDto = BeanPropertyRowMapper.newInstance(DownloadDto.class);
	    
	    public DownloadDao(DataSource dataSource) {
	    	this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	    }
	    
	    public DownloadDto getDownloadFileInfo(int fileInfoId) {
			Map<String, Integer> params = new HashMap<>();
			params.put("fileInfoId", fileInfoId);
			return jdbc.queryForObject(GET_DOWNLOAD_FILE_INFO, params, downloadDto);
		}

}