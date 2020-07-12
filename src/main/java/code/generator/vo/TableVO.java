package code.generator.vo;

import code.generator.elements.children.TableElements;
import code.generator.util.UtilsText;
import lombok.Getter;
import lombok.ToString;

/**
 *
 * @since 2019.11.25 01:20
 * @author 장진철
 *
 *         <PRE>
 * -----------------------------------------------------------
 * ※ 개정 이력		
 * yyyy.MM.dd hh:mm - 이름    : 이력
 * -----------------------------------------------------------
 * 2019.11.25 01:20 - 장진철 : 최초 작성
 *         </PRE>
 */

@Getter
@ToString
public class TableVO {

	private String orgTableName;
	private String tableName;
	private String sqlSession;
	private String desc;
	private String alias;
	private String prefix;
	private String suffix;
	private String lock;
	private String fileName;

	public TableVO(TableElements tablesElement) {
		
		this.orgTableName = this.tableName = tablesElement.getName();
		this.fileName = (tablesElement.getRename() != null) ? tablesElement.getRename(): this.tableName;
		
		this.desc = tablesElement.getDesc();
		this.alias = tablesElement.getAlias();
		this.prefix = (tablesElement.getPrefix() != null) ? tablesElement.getPrefix() + "_" : null;
		this.suffix = (tablesElement.getSuffix() != null) ?  "_" + tablesElement.getSuffix(): null;
		this.lock = tablesElement.getLock();


		if (!UtilsText.isBlank(this.prefix)) {
			fileName = UtilsText.concat(this.prefix, fileName);
		}
		
		if (!UtilsText.isBlank(this.suffix)) {
			fileName = UtilsText.concat(fileName, this.suffix);
		}

		this.fileName = UtilsText.convert2CamelCaseTable(fileName, false);
	}

}
