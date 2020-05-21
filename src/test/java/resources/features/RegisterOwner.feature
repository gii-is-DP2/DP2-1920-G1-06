Feature: RegisterOwner 
  I can do register in the system with my first name, last name, dni, bithdate, gender, email, telephone, username and password

  Scenario: Successful register as ownerUI (Positive)
    Given I am not registered in the system
    When I register as ownerUI
    Then ownerUI appears as a new user in DB
    
  Scenario: Register fail (Negative)
  	Given I am not registered in the system
    When I try to register as ownerUI with an invalid dni
    Then the login form is shown again