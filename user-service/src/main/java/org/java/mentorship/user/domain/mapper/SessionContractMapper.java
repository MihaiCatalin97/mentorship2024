package org.java.mentorship.user.domain.mapper;

import lombok.experimental.UtilityClass;
import org.java.mentorship.contracts.user.dto.Session;
import org.java.mentorship.contracts.user.dto.SessionWithKey;
import org.java.mentorship.user.domain.SessionEntity;

@UtilityClass
public class SessionContractMapper {

    public Session sessionToContract(SessionEntity sessionEntity) {
        final Session sessionContract = new Session();

        sessionContract.setId(sessionEntity.getId());
        sessionContract.setExpiresAt(sessionEntity.getExpiresAt());
        sessionContract.setUserId(sessionEntity.getUserId());

        return sessionContract;
    }

    public SessionWithKey sessionToContractWithKey(SessionEntity sessionEntity) {
        final SessionWithKey sessionContract = new SessionWithKey();

        sessionContract.setId(sessionEntity.getId());
        sessionContract.setExpiresAt(sessionEntity.getExpiresAt());
        sessionContract.setUserId(sessionEntity.getUserId());
        sessionContract.setSessionKey(sessionEntity.getSessionKey());

        return sessionContract;
    }

}
