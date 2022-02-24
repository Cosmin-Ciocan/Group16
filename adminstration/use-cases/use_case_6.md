# USE CASE: 1  Produce report of a country

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *ONU statistician* I want *to produce report of a country* so that *I can have more information about a country*

### Scope

ONU

### Level

Primary task

### Preconditions

We know the country.  Database contains current country

### Success End Condition

A report is available for the ONU statistician to provide to ONU.

### Failed End Condition

No report is produced.

### Primary Actor

ONU statistician

### Trigger

A request for country information is sent to the ONU statistician.

## MAIN SUCCESS SCENARIO

1. ONU request information for a given country.
2. ONU statistician captures country to get information for.
3. ONU statistician extracts current information of the current country.
4. ONU statistician provides report to ONU.

## EXTENSIONS

3. **Country city does not exist**:
    1. ONU statistician informs ONU no country exists.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Final release 
