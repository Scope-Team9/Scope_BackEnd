package com.studycollaboproject.scope.repository;

import java.util.List;

public interface TotalResultRepositoryExtension {
    void updateAssessmentResult(String userType, List<String> memberTypes);
}