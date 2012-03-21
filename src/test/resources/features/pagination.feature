# language: en
Feature: A customer can paginate search results
  As a customer
  I want to paginate search results

  Background:
    Given the repository:
      | title                            | description                                                                                                                                                                                                                                    | price   | reference | email                    | location.streetAddress         | location.city      | location.postalCode | location.countryCode |
      | Chef de projet marketing web h/f | Sous l’autorité du responsable marketing France, vous serez en charge de la définition d'actions marketing, de leur mise en œuvre, ainsi que de leur suivi. Votre objectif sera de développer le trafic et les ventes de nos sites partenaires | 70000.0 | REF-00001 | apply0@vente-privee.com  | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Chef de projet marketing web h/f | Sous l’autorité du responsable marketing France, vous serez en charge de la définition d'actions marketing, de leur mise en œuvre, ainsi que de leur suivi. Votre objectif sera de développer le trafic et les ventes de nos sites partenaires | 70000.0 | REF-00002 | apply1@vente-privee.com  | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Chef de projet marketing web h/f | Sous l’autorité du responsable marketing France, vous serez en charge de la définition d'actions marketing, de leur mise en œuvre, ainsi que de leur suivi. Votre objectif sera de développer le trafic et les ventes de nos sites partenaires | 70000.0 | REF-00003 | apply2@vente-privee.com  | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Chef de projet marketing web h/f | Sous l’autorité du responsable marketing France, vous serez en charge de la définition d'actions marketing, de leur mise en œuvre, ainsi que de leur suivi. Votre objectif sera de développer le trafic et les ventes de nos sites partenaires | 70000.0 | REF-00004 | apply3@vente-privee.com  | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Chef de projet marketing web h/f | Sous l’autorité du responsable marketing France, vous serez en charge de la définition d'actions marketing, de leur mise en œuvre, ainsi que de leur suivi. Votre objectif sera de développer le trafic et les ventes de nos sites partenaires | 70000.0 | REF-00005 | apply4@vente-privee.com  | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Chef de projet marketing web h/f | Sous l’autorité du responsable marketing France, vous serez en charge de la définition d'actions marketing, de leur mise en œuvre, ainsi que de leur suivi. Votre objectif sera de développer le trafic et les ventes de nos sites partenaires | 70000.0 | REF-00006 | apply5@vente-privee.com  | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Chef de projet marketing web h/f | Sous l’autorité du responsable marketing France, vous serez en charge de la définition d'actions marketing, de leur mise en œuvre, ainsi que de leur suivi. Votre objectif sera de développer le trafic et les ventes de nos sites partenaires | 70000.0 | REF-00007 | apply6@vente-privee.com  | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Chef de projet marketing web h/f | Sous l’autorité du responsable marketing France, vous serez en charge de la définition d'actions marketing, de leur mise en œuvre, ainsi que de leur suivi. Votre objectif sera de développer le trafic et les ventes de nos sites partenaires | 70000.0 | REF-00008 | apply7@vente-privee.com  | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Chef de projet marketing web h/f | Sous l’autorité du responsable marketing France, vous serez en charge de la définition d'actions marketing, de leur mise en œuvre, ainsi que de leur suivi. Votre objectif sera de développer le trafic et les ventes de nos sites partenaires | 70000.0 | REF-00009 | apply8@vente-privee.com  | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Chef de projet marketing web h/f | Sous l’autorité du responsable marketing France, vous serez en charge de la définition d'actions marketing, de leur mise en œuvre, ainsi que de leur suivi. Votre objectif sera de développer le trafic et les ventes de nos sites partenaires | 70000.0 | REF-00010 | apply9@vente-privee.com  | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Chef de projet marketing web h/f | Sous l’autorité du responsable marketing France, vous serez en charge de la définition d'actions marketing, de leur mise en œuvre, ainsi que de leur suivi. Votre objectif sera de développer le trafic et les ventes de nos sites partenaires | 70000.0 | REF-00011 | apply10@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Chef de projet marketing web h/f | Sous l’autorité du responsable marketing France, vous serez en charge de la définition d'actions marketing, de leur mise en œuvre, ainsi que de leur suivi. Votre objectif sera de développer le trafic et les ventes de nos sites partenaires | 70000.0 | REF-00012 | apply11@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Chef de projet marketing web h/f | Sous l’autorité du responsable marketing France, vous serez en charge de la définition d'actions marketing, de leur mise en œuvre, ainsi que de leur suivi. Votre objectif sera de développer le trafic et les ventes de nos sites partenaires | 70000.0 | REF-00013 | apply12@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Chef de projet marketing web h/f | Sous l’autorité du responsable marketing France, vous serez en charge de la définition d'actions marketing, de leur mise en œuvre, ainsi que de leur suivi. Votre objectif sera de développer le trafic et les ventes de nos sites partenaires | 70000.0 | REF-00014 | apply13@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Chef de projet marketing web h/f | Sous l’autorité du responsable marketing France, vous serez en charge de la définition d'actions marketing, de leur mise en œuvre, ainsi que de leur suivi. Votre objectif sera de développer le trafic et les ventes de nos sites partenaires | 70000.0 | REF-00015 | apply14@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Chef de projet marketing web h/f | Sous l’autorité du responsable marketing France, vous serez en charge de la définition d'actions marketing, de leur mise en œuvre, ainsi que de leur suivi. Votre objectif sera de développer le trafic et les ventes de nos sites partenaires | 70000.0 | REF-00016 | apply15@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Chef de projet marketing web h/f | Sous l’autorité du responsable marketing France, vous serez en charge de la définition d'actions marketing, de leur mise en œuvre, ainsi que de leur suivi. Votre objectif sera de développer le trafic et les ventes de nos sites partenaires | 70000.0 | REF-00017 | apply16@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Chef de projet marketing web h/f | Sous l’autorité du responsable marketing France, vous serez en charge de la définition d'actions marketing, de leur mise en œuvre, ainsi que de leur suivi. Votre objectif sera de développer le trafic et les ventes de nos sites partenaires | 70000.0 | REF-00018 | apply17@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Chef de projet marketing web h/f | Sous l’autorité du responsable marketing France, vous serez en charge de la définition d'actions marketing, de leur mise en œuvre, ainsi que de leur suivi. Votre objectif sera de développer le trafic et les ventes de nos sites partenaires | 70000.0 | REF-00019 | apply18@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |

  @done
  @#12

  Scenario: Representation should not contain links when they are not relevant
    Given I am a customer
    When I ask for page "2"
    And I ask "2" items per page
    And I sort by "descending" "created"
    And I search classifieds for which "description" contains "xbox"
    Then I get "0" items
    And the representation should contain no "first" link
    And the representation should contain no "previous" link
    And the representation should contain no "self" link
    And the representation should contain no "next" link
    And the representation should contain no "last" link

  @done
  @#13

  Scenario: Representation should contain links when they are relevant
    Given I am a customer
    When I ask for page "3"
    And I ask "3" items per page
    And I sort by "descending" "created"
    And I search classifieds for which "description" contains "marketing"
    Then I get "19" items
    And "first" link "page index" should be "0"
    And "previous" link "pageIndex" should be "2"
    And "self" link "pageIndex" should be "3"
    And "next" link "pageIndex" should be "4"
    And "last" link "pageIndex" should be "6"

  @done
  @#15

  Scenario: A customer can ask for a specific page index
    Given I am a customer
    When I ask for page "4"
    And I search all classifieds
    Then I get "19" items
    And the items are:
      | title                            | price   | reference | email                    | location.streetAddress         | location.city      | location.postalCode | location.countryCode |
      | Chef de projet marketing web h/f | 70000.0 | REF-00011 | apply10@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Chef de projet marketing web h/f | 70000.0 | REF-00010 | apply9@vente-privee.com  | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |

  @done
  @#16

  Scenario: A customer can specify items per page
    Given I am a customer
    And I ask "3" items per page
    And I search all classifieds
    Then I get "19" items
    And the items are:
      | title                            | price   | reference | email                    | location.streetAddress         | location.city      | location.postalCode | location.countryCode |
      | Chef de projet marketing web h/f | 70000.0 | REF-00019 | apply18@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Chef de projet marketing web h/f | 70000.0 | REF-00018 | apply17@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Chef de projet marketing web h/f | 70000.0 | REF-00017 | apply16@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |

  @done
  @#17

  Scenario: A customer can specify sort
    Given I am a customer
    And I ask "3" items per page
    And I sort by "ascending" "reference"
    And I search all classifieds
    Then I get "19" items
    And the items are:
      | title                            | price   | reference | email                   | location.streetAddress         | location.city      | location.postalCode | location.countryCode |
      | Chef de projet marketing web h/f | 70000.0 | REF-00001 | apply0@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Chef de projet marketing web h/f | 70000.0 | REF-00002 | apply1@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Chef de projet marketing web h/f | 70000.0 | REF-00003 | apply2@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |

