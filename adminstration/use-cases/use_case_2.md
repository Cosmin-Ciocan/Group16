# USE CASE: 1  Produce a population report of a region

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *ONU statistician* I want *to produce a population report of a region* so that *I can have more information about a population of a region*

### Scope

ONU

### Level

Primary task

### Preconditions

We know the region.  Database contains current region & population

### Success End Condition

A report is available for the ONU statistician to provide to ONU.

### Failed End Condition

No report is produced.

### Primary Actor

ONU statistician

### Trigger

A request for region population information is sent to the ONU statistician.

## MAIN SUCCESS SCENARIO

1. ONU request population information for a given region.
2. ONU statistician captures region to get population information for.
3. ONU statistician extracts current population information of the current region.
4. ONU statistician provides report to ONU.

## EXTENSIONS

3. **Region does not exist**:
    1. ONU statistician informs ONU no region exists.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Final release 
