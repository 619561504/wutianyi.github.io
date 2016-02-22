/**
 * hanjiewu 
 * 2015年11月30日:下午7:41:59
 */
package com.wutianyi.batch;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author hanjiewu
 *
 */
public class ProductItemWriter implements ItemWriter<Product> {
	private static final String GET_PRODUCT = "select * from PRODUCT where id = ?";
	private static final String INSERT_PRODUCT = "insert into PRODUCT (id,name,description,quantity) values (?,?,?,?)";
	private static final String UPDATE_PRODUCT = "update PRODUCT set name = ?, description = ?,quantity = ? where id =?";

	@Autowired
	private JdbcTemplate jdbctemplate;

	/* 
	 * 
	 */
	@Override
	public void write(List<? extends Product> items) throws Exception {
		for (Product product : items) {
			List<Product> productLists = jdbctemplate.query(GET_PRODUCT, new Object[] { product.getId() },
					new RowMapper<Product>() {

						@Override
						public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
							Product p = new Product();
							p.setId(rs.getInt(1));
							p.setName(rs.getString(2));
							p.setDescription(rs.getString(3));
							p.setQuantity(rs.getInt(4));
							return p;
						}
					});
			if (productLists.size() > 0) {
				jdbctemplate.update(UPDATE_PRODUCT, product.getName(), product.getDescription(), product.getQuantity(),
						product.getId());
			} else {
				jdbctemplate.update(INSERT_PRODUCT, product.getId(), product.getName(), product.getDescription(),
						product.getQuantity());
			}
		}
	}

}
