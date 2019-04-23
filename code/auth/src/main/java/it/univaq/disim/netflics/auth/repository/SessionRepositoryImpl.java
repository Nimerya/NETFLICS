package it.univaq.disim.netflics.auth.repository;

import it.univaq.disim.netflics.auth.BusinessException;
import it.univaq.disim.netflics.auth.model.Session;
import it.univaq.disim.netflics.auth.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@SuppressWarnings("Duplicates")
@Repository
public class SessionRepositoryImpl implements SessionRepository {

    private static Logger LOGGER = LoggerFactory.getLogger(SessionRepositoryImpl.class);

    @Autowired
    private DataSource dataSource;


    public Session findByToken(String token) {

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        Session s = new Session();

        String sql = String.format("SELECT * FROM session WHERE token = '%s'", token);

        LOGGER.info("query: {}", sql);

        try {
            con = dataSource.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(sql);

            if(rs.next()){

                s.setId(rs.getLong("id"));
                s.setToken(rs.getString("token"));
                s.setUserId(rs.getLong("user_id"));

            }else{
                LOGGER.info("Token not valid");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }
        return s;
    }

    public Session save(Session s) {

        Connection con = null;
        Statement st = null;
        Integer rs = null;

        String sql = String.format("INSERT INTO session (user_id, token) VALUES (%d, '%s')", s.getUserId(), s.getToken());

        LOGGER.info("query: {}", sql);

        try {
            con = dataSource.getConnection();
            st = con.createStatement();
            rs = st.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }
        return s;
    }

    public void deleteAll (User u){
        Connection con = null;
        Statement st = null;
        Integer rs = null;

        String sql = String.format("DELETE FROM session WHERE user_id = %d", u.getId());

        LOGGER.info("query: {}", sql);

        try {
            con = dataSource.getConnection();
            st = con.createStatement();
            rs = st.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }

    }

    public void delete (String token){
        Connection con = null;
        Statement st = null;
        Integer rs = null;

        String sql = String.format("DELETE FROM session WHERE token = '%s'", token);

        LOGGER.info("query: {}", sql);

        try {
            con = dataSource.getConnection();
            st = con.createStatement();
            rs = st.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public String generateToken(){

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String sql = "SELECT UUID()";

        String token = "";

        LOGGER.info("query: {}", sql);

        try {
            con = dataSource.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(sql);


            if(rs.next()){
                token = rs.getString("UUID()");
            }else{
                LOGGER.error("could not generate token");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }
        return token;
    }
}
