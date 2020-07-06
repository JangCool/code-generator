package code.generator.common;

import org.w3c.dom.Element;

import code.generator.parser.XmlParser;
import code.generator.util.UtilsText;
import code.generator.vo.PathVO;

public class Global {

	private static PathVO path;
	private static String controllerPkg = Const.DEFAULT_PATH_CONTROLLER_PACKAGE;
	private static String servicePkg = Const.DEFAULT_PATH_SERVICE_PACKAGE;
	private static String clientPkg = Const.DEFAULT_PATH_CLIENT_PACKAGE;
	private static String daoPkg = Const.DEFAULT_PATH_DAO_PACKAGE;
	private static String sqlSession = Const.DEFAULT_SQL_SESSION;
	private static String sqlSessionUseYn = "Y";
	private static String regDateColumn = Const.DEFAULT_MAPPER_REG_DATE_COLUMN;
	private static String modDateColumn = Const.DEFAULT_MAPPER_MOD_DATE_COLUMN;

	private static String excludeInsertColumn = Const.DEFAULT_MAPPER_EXCLUDE_INSERT_COLUMN;
	private static String excludeUpdateColumn = Const.DEFAULT_MAPPER_EXCLUDE_UPDATE_COLUMN;

	public static void init(XmlParser xp) {
		Element global = (Element) xp.getDoc().getElementsByTagName("global").item(0);

		PathVO pv = new PathVO();

		if (global != null) {
			Element pathEl = (Element) global.getElementsByTagName("path").item(0);
			Element sqlSessionEl = (Element) global.getElementsByTagName("sqlsession").item(0);
			Element daoPkgEl = (Element) global.getElementsByTagName("daopkg").item(0);
			Element controllerPkgEl = (Element) global.getElementsByTagName("controllerpkg").item(0);
			Element servicePkgEl = (Element) global.getElementsByTagName("servicepkg").item(0);
			Element clientPkgEl = (Element) global.getElementsByTagName("clientpkg").item(0);
			Element defaultDateEl = (Element) global.getElementsByTagName("default-date").item(0);
			Element excludeColumnEl = (Element) global.getElementsByTagName("excludeColumn").item(0);

			if (pathEl != null) {

				Element template = (Element) pathEl.getElementsByTagName("template").item(0);
				Element source = (Element) pathEl.getElementsByTagName("source").item(0);
				Element mapper = (Element) pathEl.getElementsByTagName("mapper").item(0);
				Element view = (Element) pathEl.getElementsByTagName("view").item(0);
				Element javascript = (Element) pathEl.getElementsByTagName("javascript").item(0);

				pv.setTemplate(getTarget(template, null));
				pv.setSource(getTarget(source, Const.DEFAULT_PATH_SOURCES));
				pv.setMapper(getTarget(mapper, Const.DEFAULT_PATH_MAPPERS));
				pv.setView(getTarget(view, Const.DEFAULT_PATH_VIEWS));
				pv.setJavascript(getTarget(javascript, Const.DEFAULT_PATH_JAVASCRIPTS));

			} else {
				pv.setTemplate(null);
				pv.setSource(Const.DEFAULT_PATH_SOURCES);
				pv.setMapper(Const.DEFAULT_PATH_MAPPERS);
				pv.setView(Const.DEFAULT_PATH_VIEWS);
				pv.setJavascript(Const.DEFAULT_PATH_JAVASCRIPTS);

			}

			if (sqlSessionEl != null) {
				String useYn = getTarget(sqlSessionEl, "Y", "use");
				if (!"N".equalsIgnoreCase(useYn)) {
					Global.sqlSession = getTarget(sqlSessionEl, Const.DEFAULT_SQL_SESSION, "name");
				}

				Global.sqlSessionUseYn = useYn;

			}

			if (daoPkgEl != null) {
				Global.daoPkg = getTarget(daoPkgEl, Const.DEFAULT_PATH_DAO_PACKAGE);
			}

			if (controllerPkgEl != null) {
				Global.controllerPkg = getTarget(controllerPkgEl, Const.DEFAULT_PATH_CONTROLLER_PACKAGE);
			}

			if (servicePkgEl != null) {
				Global.servicePkg = getTarget(servicePkgEl, Const.DEFAULT_PATH_SERVICE_PACKAGE);
			}

			if (clientPkgEl != null) {
				Global.clientPkg = getTarget(clientPkgEl, Const.DEFAULT_PATH_CLIENT_PACKAGE);
			}

			if (defaultDateEl != null) {
				Global.regDateColumn = getTarget(defaultDateEl, Const.DEFAULT_MAPPER_REG_DATE_COLUMN,
						"reg-date-column");
				Global.modDateColumn = getTarget(defaultDateEl, Const.DEFAULT_MAPPER_MOD_DATE_COLUMN,
						"mod-date-column");
			}

			if (excludeColumnEl != null) {

				Element insertEl = (Element) excludeColumnEl.getElementsByTagName("insert").item(0);
				Element updateEl = (Element) excludeColumnEl.getElementsByTagName("update").item(0);
				Global.excludeInsertColumn = getTarget(insertEl, Const.DEFAULT_MAPPER_EXCLUDE_INSERT_COLUMN, "exclude");
				Global.excludeUpdateColumn = getTarget(updateEl, Const.DEFAULT_MAPPER_EXCLUDE_UPDATE_COLUMN, "exclude");
			}
		} else {
			pv.setTemplate(null);
			pv.setSource(Const.DEFAULT_PATH_SOURCES);
			pv.setMapper(Const.DEFAULT_PATH_MAPPERS);
			pv.setView(Const.DEFAULT_PATH_VIEWS);
			pv.setJavascript(Const.DEFAULT_PATH_JAVASCRIPTS);

		}

		Global.path = pv;

	}

	public static String getTarget(Element pathChild, String defaultTarget) {
		return getTarget(pathChild, defaultTarget, "target");
	}

	public static String getTarget(Element pathChild, String defaultTarget, String attribute) {

		String target = defaultTarget;

		if (pathChild != null) {
			String attrTarget = pathChild.getAttribute(attribute);
			target = Config.getPropertyKey(attrTarget);

			if (target == null) {

				if (UtilsText.isBlank(attrTarget)) {
					target = defaultTarget;
				} else {
					target = attrTarget;
				}
			}
		}

		return target;
	}

	public static PathVO getPath() {
		return path;
	}

	public static String getControllerPkg() {
		return controllerPkg;
	}

	public static String getServicePkg() {
		return servicePkg;
	}

	public static String getClientPkg() {
		return clientPkg;
	}

	public static String getDaoPkg() {
		return daoPkg;
	}

	public static String getSqlSession() {
		return sqlSession;
	}

	public static String getSqlSessionUseYn() {
		return sqlSessionUseYn;
	}

	public static void setPath(PathVO path) {
		Global.path = path;
	}

	public static String getRegDateColumn() {
		return regDateColumn;
	}

	public static String getModDateColumn() {
		return modDateColumn;
	}

	public static String getExcludeInsertColumn() {
		return excludeInsertColumn;
	}

	public static String getExcludeUpdateColumn() {
		return excludeUpdateColumn;
	}

}
