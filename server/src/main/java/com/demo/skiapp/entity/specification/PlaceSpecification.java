package com.demo.skiapp.entity.specification;

import com.demo.skiapp.entity.PlaceEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class PlaceSpecification implements Specification<PlaceEntity> {

    private final String search;

    public PlaceSpecification(String search) {
        this.search = search;
    }

    @Override
    public Predicate toPredicate(Root<PlaceEntity> root, CriteriaQuery<?> query,
                                 CriteriaBuilder cb) {
        if (StringUtils.hasText(search)) {
            final String toSearch = "%" + search.toLowerCase() + "%";
            Predicate matches = cb.disjunction();
            matches.getExpressions().addAll(List.of(
                    cb.like(cb.lower(root.get("placeName")), toSearch),
                    cb.like(cb.lower(root.get("zone")), toSearch),
                    cb.like(cb.lower(root.get("region")), toSearch),
                    cb.like(cb.lower(root.get("country")), toSearch)
            ));
            return matches;
        }
        return null;
    }
}
