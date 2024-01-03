package com.kh.springchap3.service;

import com.kh.springchap3.model.MemberGoogle;

public interface MemberGoogleService {
	MemberGoogle findByUsername(String username);
    void saveUser(MemberGoogle user);
}
