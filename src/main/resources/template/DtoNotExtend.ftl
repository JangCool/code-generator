package ${package};

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 테이블 명  : ${tableName}
 * 설명        : ${desc}
 *
 * ※  DB TABLE 컬럼명으로 각 field를  생성한다. 
 *
 *   - FORM(VO)데이터 역할로 사용 할 때 필요한 field가 없을 경우 - Form데이터 매핑은 되도록 페이지별로 만들어서 사용 하고, 어쩔수 없을 때 [최소]한으로 사용한다.
 *   - Query JOIN 시 Model클래스에 특정 컬럼(field)가 없을 경우
 *   - 로직 처리시 필요한 구분 값 또는 결과 값을 저장할 필요가 있을 경우.
 */
@Slf4j
@Data
public class ${fileName} {



}
