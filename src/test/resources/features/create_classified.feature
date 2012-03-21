# language: en
Feature: A customer can create adverts
  As a customer
  I want to create classifieds

  @done
  @#1
  Scenario Outline: A customer can create a classified
    Given I am a customer
    And a valid classified
    And I send "<format>"
    When I try to create the classified
    Then the creation is successful
    When I load the classified as "<format>"
    Then its status is "draft"
  Examples:
    | format           |
    | application/json |
    | application/xml  |

  @done
  @#2

  Scenario Outline: A customer can't create an classified with an invalid title
    Given I am a customer
    And I use "<language>" language
    And I send "application/json"
    And a valid classified
    And I set "title" to "<wrong_data>"
    When I try to create the classified
    Then the creation fails
    And the error message is "<error_message>"
  Examples:
    | language | wrong_data                                                    | error_message                 |
    | en       |                                                               | Title is required             |
    | en       | aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa5 | Title max length is 50        |
    | fr       |                                                               | Le titre est requis           |
    | fr       | aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa5 | Taille maximale du titre : 50 |
