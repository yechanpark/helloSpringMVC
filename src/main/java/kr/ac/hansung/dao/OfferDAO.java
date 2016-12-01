package kr.ac.hansung.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.ac.hansung.model.Offer;

@Repository
public class OfferDAO {
	// �� ��Ҹ� ���ؼ� sql Statement Ȱ��
	private JdbcTemplate jdbcTemplateObject;

	// DI�� �ܺο��� DataSource����
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public int getRowCount() {
		String sqlStatement = "select count(*) from offers";
		// queryForObject()�� �ϳ��� ������Ʈ( count(*)�� ����� 3 )�� integer Ÿ������ �ѱ��.
		return jdbcTemplateObject.queryForObject(sqlStatement, Integer.class);
	}

	// querying and returning a single object
	public Offer getOffer(String name) {
		// ?�� placeholder
		String sqlStatement = "select * from offers where name=?";

		/*
		 * use Anonymous Class
		 * return jdbcTemplateObject.queryForObject(sqlStatement, new Object[] { name
		 * }, new RowMapper<Offer>() {
		 * 
		 * public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
		 * Offer offer = new Offer();
		 * 
		 * offer.setId(rs.getInt("id")); offer.setName(rs.getString("name"));
		 * offer.setEmail(rs.getString("email"));
		 * offer.setText(rs.getString("text"));
		 * 
		 * return offer; } });
		 */

		// not Anonymous
		return jdbcTemplateObject.queryForObject(sqlStatement, new Object[] { name }, new OfferMapper());
	}

	// querying and returning multiple objects
	public List<Offer> getOffers() {
		String sqlStatement = "select * from offers";

		/*
		 * use Anonymous Class
		 * return jdbcTemplateObject.query(sqlStatement, new
		 * RowMapper<Offer>() {
		 * 
		 * public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
		 * Offer offer = new Offer();
		 * 
		 * offer.setId(rs.getInt("id")); offer.setName(rs.getString("name"));
		 * offer.setEmail(rs.getString("email"));
		 * offer.setText(rs.getString("text"));
		 * 
		 * return offer; } });
		 */

		// not Anonymous
		return jdbcTemplateObject.query(sqlStatement, new OfferMapper());
	}

	public boolean insert(Offer offer) {

		String name = offer.getName();
		String email = offer.getEmail();
		String text = offer.getText();

		String sqlStatement = "insert into offers (name, email, text) values (?,?,?)";
		return (jdbcTemplateObject.update(sqlStatement, new Object[] { name, email, text }) == 1);
	}

	public boolean update(Offer offer) {

		int id = offer.getId();
		String name = offer.getName();
		String email = offer.getEmail();
		String text = offer.getText();

		String sqlStatement = "update offers set name=?, email=?, text=? where id=?";
		return (jdbcTemplateObject.update(sqlStatement, new Object[] { name, email, text, id }) == 1);
	}

	public boolean delete(int id) {
		String sqlStatement = "delete from offers where id=?";
		return (jdbcTemplateObject.update(sqlStatement, new Object[] { id }) == 1);
	}
}
