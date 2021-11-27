package com.studycollaboproject.scope.repository;

import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.Team;
import com.studycollaboproject.scope.model.User;

import java.util.List;

public interface TotalResultRepositoryExtension {
    void updateAssessmentResult(String userType, List<String> memberTypes);
}