Feature: Arendehantering

  # 01 - Uppdatera Färdtjänstkort
  #
  #
  #



  # 0001 - Skapa Färdtjänstkort Fotodispens: Ja, Tidsbegränsning: Nej
  Scenario: Create a Arende_Fardtjanstkort 0001
    Given user is logged in in SoFT
    Given a Resenar is selected

    # Roller verkar inte vara godkända i Gherkin. Svenska tecken fungerar inte så bra. Kolla upp om det finns lösning
    # Given the following roles:
    #  | SoFT CRM |
    #  | SoFT CRM1 |

    Then user selects create new Arende_Fardtjanstkort
    And user enters Fardbevistyp as Fardtjanstkort
    And SoFT verifies Resenar is correct

	# Fotodispens är satt till Ja
    And user sets Fotodispens to Ja
    And user sets Fotodispens_Giltigt_Till to a date in the future

    # Registreringsorsak
    And user enters Registreringsorsak

	# Tidsbegränsning: Ingen tidsbegränsning
    And user enters Tidsbegransning

    # Automatisk validering i systemet
    When user saves Arende_Fardtjanstkort
    Then SoFT validates that no duplicate draft for Arende_Fardtjanstkort exists
    And SoFT validates that Farbevistyp is entered
    And SoFT validates that Resenar is entered
    And SoFT validates that Resenar status is Aktiv
    And SoFT validates that Registreringsorsak is selected
    And SoFT validates that Fotodispens is entered with value Ja
    And SoFT validates that Fotodispens_Giltigt_Till is set to a date in the future
    And SoFT validates that Fardbeviset_Giltig_Till is set to todays date or a date in the future
	# Kontrollera roller. Skrivs om.
    And SoFT validates that user or user_team has at least one role in

    And SoFT validates that Fardbeviset gets a unique Id_Nummer
    And SoFT validates that Fardbeviset_Giltig_Fran is set to todays date
    And SoFT validates that Fardbeviset_Namn is set according to Namnstandard
    And SoFT validates that Fardbeviset_Status is set to Utkast
    And SoFT validates that Fardbeviset gets an Fardbeviset_Identitetssok_Referens

