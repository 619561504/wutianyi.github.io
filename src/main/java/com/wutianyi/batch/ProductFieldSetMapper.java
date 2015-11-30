/**
 * hanjiewu 
 * 2015年11月30日:下午7:36:32
 */
package com.wutianyi.batch;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * @author hanjiewu
 *
 */
public class ProductFieldSetMapper implements FieldSetMapper<Product> {

	/* 
	 * 
	 */
	@Override
	public Product mapFieldSet(FieldSet fieldSet) throws BindException {
		Product product = new Product();
		product.setId(fieldSet.readInt("id"));
		product.setName(fieldSet.readString("name"));
		product.setDescription(fieldSet.readString("description"));
		product.setQuantity(fieldSet.readInt("quantity"));
		return product;
	}

}
