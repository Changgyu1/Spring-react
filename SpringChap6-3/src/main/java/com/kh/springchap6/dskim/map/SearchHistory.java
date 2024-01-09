package com.kh.springchap6.dskim.map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class SearchHistory {
	private @Id @GeneratedValue Long id;
	private String keyWord;

	private @ManyToOne UserAccount userAccount;
	
	private String createdDtm;

	private SearchHistory() {}

	public SearchHistory(String keyWord, String createdDtm, UserAccount userAccount) {
		this.keyWord = keyWord;
		this.createdDtm = createdDtm;
		this.userAccount = userAccount;
	}
}
