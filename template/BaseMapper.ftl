<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperid}">

    <!--
		※ 테이블 명  : ${tableName}
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
    
    <select id="select" parameterType="${model}" resultType="${model}">
    
     /*+ ${mapperid}.select [기본 조회 쿼리] [Generator] */
    
	${select} 
    </select>
    
    <!--
    	※ 경고
		이 selectByPrimaryKey SQL은  Code Generator를 통하여 생성 되었습니다.
     	기본 쿼리 이고 수시로 변경 될 소지가 있기 떄문에 Generator로 변경 하는 것이 아닌 직접 수정은 하지 마십시요.
     	
    -->
    
    <select id="selectByPrimaryKey" parameterType="${model}" resultType="${model}">
    
     /*+ ${mapperid}.selectByPrimaryKey [기본 PK 조회 쿼리] [Generator] */  
    
	${selectByPrimaryKey} 
    </select>
    
    <!--
    	※ 경고
		이 insert SQL은  Code Generator를 통하여 생성 되었습니다.
     	기본 쿼리 이고 수시로 변경 될 소지가 있기 떄문에 Generator로 변경 하는 것이 아닌 직접 수정은 하지 마십시요.
     	
    -->
    
<#if insertKeyGenerator??>
	<insert id="insert" useGeneratedKeys="true" keyProperty="${insertKeyGenerator}">
<#else>
    <insert id="insert" parameterType="${model}">	
</#if>

     /*+ ${mapperid}.insert [기본 insert 쿼리] [Generator] */
    
	${insert} 
    </insert>
    
    <!--
    	※ 경고
		이 update SQL은  Code Generator를 통하여 생성 되었습니다.
     	기본 쿼리 이고 수시로 변경 될 소지가 있기 떄문에 Generator로 변경 하는 것이 아닌 직접 수정은 하지 마십시요.
     	
    -->
    <update id="update" parameterType="${model}">
    
     /*+ ${mapperid}.update [기본 update 쿼리] [Generator] */
    
	${update}   
    </update>
    
    <!--
    	※ 경고
		이 delete SQL은  Code Generator를 통하여 생성 되었습니다.
     	기본 쿼리 이고 수시로 변경 될 소지가 있기 떄문에 Generator로 변경 하는 것이 아닌 직접 수정은 하지 마십시요.
     	
    -->
    <delete id="delete" parameterType="${model}">
    
     /*+ ${mapperid}.delete [기본 delete 쿼리] [Generator] */
    
	${delete}  
    </delete>
</mapper>