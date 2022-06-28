package com.example.auth.repository;

import com.example.auth.model.transactions.Offer;
import com.example.auth.model.users.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository{

  @Autowired
  MongoTemplate mongoTemplate;

  @Override
  public List<UserModel> findUserByProperties(String username, String fullName, Offer offer, String email, Pageable pageable) {
    final Query query = new Query().with(pageable);
    final List<Criteria> criteria = new ArrayList<>();
    if (username != null && !username.isEmpty())
      criteria.add(Criteria.where("username").is(username));
    if (fullName != null && !fullName.isEmpty())
      criteria.add(Criteria.where("fullName").is(fullName));
    if (offer != null )
      criteria.add(Criteria.where("offer").in(offer));
    if (email != null && !email.isEmpty())
      criteria.add(Criteria.where("email").is(email));

    if (!criteria.isEmpty())
      query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
    return mongoTemplate.find(query, UserModel.class);
  }
}
