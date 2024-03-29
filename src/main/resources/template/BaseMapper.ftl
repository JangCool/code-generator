<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperid}">

    <!--
		※ 테이블 명  : ${name}
		※설명         : ${desc}

    	※ 경고
		이 select SQL은  Code Generator를 통하여 생성 되었습니다.
     	기본 쿼리 이고 수시로 변경 될 소지가 있기 떄문에 Generator로 변경 하는 것이 아닌 직접 수정은 하지 마십시요.
    -->
    
    <resultMap id="Base${fileName}ResultMap" type="${model}" >
    	${resultMap}
    </resultMap>
    
    <sql id="select-columns">
    	${columns}
    </sql>
    
    <sql id="pk-columns">
    	${pkcolumns}
    </sql>
    
</mapper>