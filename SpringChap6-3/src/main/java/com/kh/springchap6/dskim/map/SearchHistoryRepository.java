package com.kh.springchap6.dskim.map;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.dskim.map.SearchHistory;

public interface SearchHistoryRepository extends PagingAndSortingRepository<SearchHistory, Long>{

	@Override
	SearchHistory save(@Param("searchHistory") SearchHistory searchHistory);
}
