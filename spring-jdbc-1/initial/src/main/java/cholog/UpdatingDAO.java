package cholog;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class UpdatingDAO {
    private JdbcTemplate jdbcTemplate;

    public UpdatingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
    private final RowMapper<Customer> actorRowMapper = (resultSet, rowNum) -> {
        Customer customer = new Customer(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name")
        );
        return customer;
    };
    추후 rowMapper에 대해 학습해보고 이용해보기
    */

    /**
     * public int update(String sql, @Nullable Object... args)
     */
    public void insert(Customer customer) {
        //todo: customer를 디비에 저장하기
        String query = "INSERT INTO customers (first_name, last_name) VALUES (?, ?)";
        jdbcTemplate.update(query, customer.getFirstName(), customer.getLastName());
    }
    /**
     * public int update(String sql, @Nullable Object... args)
     */
    public int delete(Long id) {
        //todo: id에 해당하는 customer를 지우고, 해당 쿼리에 영향받는 row 수반환하기
        String query = "DELETE FROM customers WHERE id = ?";
        final int deleted = jdbcTemplate.update(query, id);
        return deleted;
    }

    /**
     * public int update(final PreparedStatementCreator psc, final KeyHolder generatedKeyHolder)
     */
    public Long insertWithKeyHolder(Customer customer) {
        String sql = "insert into customers (first_name, last_name) values (?, ?)";
        // KeyHolder - JDBC insert 쿼리를 실행한 후 반환되는 값을 가져올 수 있음
        KeyHolder keyHolder = new GeneratedKeyHolder();

        //todo : keyHolder에 대해 학습하고, Customer를 저장후 저장된 Customer의 id를 반환하기
        jdbcTemplate.update(connection -> {
            // 두 번째 파라미터(= columnNames) : 작업에 영향을 받은 row 혹은 row들에 대해서 어떤 필드 값을 리턴할 것인지 지정
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            return preparedStatement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return keyHolder.getKey().longValue();
    }
}
