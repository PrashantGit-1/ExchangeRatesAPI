Feature: Title of your feature
  Verify GET Operations for exchage rates

  
  Scenario: Verify response code of exchange API on success
    Given Exchange rate API
    When Get latest Rates
    Then response code is 200
	
	Scenario: Verify todays date is returned by exchange rate API
    Given Exchange rate API
    When Get latest Rates
    Then Todays date is returned
	
	Scenario: Verify base is set to EURO for exchange rate API
    Given Exchange rate API
    When Get latest Rates
    Then Verify Base is set to EURO
	
	Scenario: Verify response code of exchange rate API for Specific date
    Given Exchange rate API
    When Get latest Rates
    Then Verify response code for Specific date
    
	Scenario: Verify exchange rate of INR for Specific date
    Given Exchange rate API
    When Get latest Rates
    Then Verify exchange rate of INR for Specific date
     
	Scenario: Verify error message is displayed by exchange rate API when future date is specified.
    Given Exchange rate API
    When Get latest Rates
    Then Verify error msg for future date
	