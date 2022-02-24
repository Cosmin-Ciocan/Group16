# USE CASE: 1  Produce report of a capital city

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *ONU statistician* I want *to produce report of a capital city* so that *I can have more information about a capital city*

### Scope

ONU

### Level

Primary task

### Preconditions

We know the capital city.  Database contains current capital city

### Success End Condition

A report is available for the ONU statistician to provide to ONU.

### Failed End Condition

No report is produced.

### Primary Actor

ONU statistician

### Trigger

A request for capital city information is sent to the ONU statistician.

## MAIN SUCCESS SCENARIO

1. ONU request information for a given capital city.
2. ONU statistician captures capital city to get information for.
3. ONU statistician extracts current information of the current capital city.
4. ONU statistician provides report to ONU.

## EXTENSIONS

3. **Capital city does not exist**:
    1. ONU statistician informs ONU no capital city exists.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Final release 
