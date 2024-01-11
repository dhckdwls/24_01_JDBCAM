package com.KoreaIT.java.JDBCAM.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.JDBCAM.Article;
import com.KoreaIT.java.JDBCAM.util.DBUtil;
import com.KoreaIT.java.JDBCAM.util.SecSql;

public class ArticleDao {
	private Connection conn;

	public ArticleDao(Connection conn) {
		this.conn = conn;
	}

	public int doWrite(String title, String body) {

		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW(),");
		sql.append("updateDate = NOW(),");
		sql.append("title = ?,", title);
		sql.append("`body`= ?;", body);

		return DBUtil.insert(conn, sql);
	}

	public List<Article> showList() {
		
		List<Article> articles = new ArrayList<>();

		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("ORDER BY id DESC;");

		List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

		for (Map<String, Object> articleMap : articleListMap) {
			articles.add(new Article(articleMap));
		}
		
		return articles;

		
		
	}

	public  int doUpdate(int id, String title, String body) {
		
		SecSql sql = new SecSql();

		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW()");
		if (title.length() > 0) {
			sql.append(",title = ?", title);
		}
		if (body.length() > 0) {
			sql.append(",`body`= ?", body);
		}
		sql.append("WHERE id = ?;", id);

		int number =DBUtil.update(conn, sql);
		return number;
		
	}

	public Map<String, Object> foundArticle(int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?;", id);

		Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
		
		return articleMap;
		
		
		
	}

	public Map<String, Object> showDetail(int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?;", id);

		Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
		
		return articleMap;
		
	}

	public int doDelete(int id,Connection conn) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?;", id);

		int number = DBUtil.delete(conn, sql);
		return number;
	}

}