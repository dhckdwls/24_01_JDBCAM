package com.KoreaIT.java.JDBCAM.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.JDBCAM.Article;
import com.KoreaIT.java.JDBCAM.dao.ArticleDao;
import com.KoreaIT.java.JDBCAM.util.DBUtil;
import com.KoreaIT.java.JDBCAM.util.SecSql;

public class ArticleService {

	private ArticleDao articleDao;

	public ArticleService(Connection conn) {
		this.articleDao = new ArticleDao(conn);
	}

	public int doWrite(String title, String body) {
		return articleDao.doWrite(title, body);
	}

	public List<Article> showList() {

		List<Article> articles = articleDao.showList();

		return articles;

	}

	public int doUpdate(int id, String title, String body) {

		int number = articleDao.doUpdate(id, title, body);

		return number;

	}

	public Map<String, Object> showDetail(int id) {
		Map<String, Object> articleMap = articleDao.foundArticle(id);
		return articleMap;

	}

	public Map<String, Object> foundArticle(int id) {
		Map<String, Object> foundArticleMap = articleDao.foundArticle(id);
		return foundArticleMap;
	}

	public int doDelete(int id,Connection conn) {
		int number = articleDao.doDelete(id,conn);
		return number;
		
		
	}

}