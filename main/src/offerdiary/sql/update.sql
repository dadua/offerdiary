update CARDS set PAYMENT_CHANNEL = 'UNKNOWN' where PAYMENT_CHANNEL is null;
update OFFERS set SOURCE_TYPE = 'UNKOWN' where SOURCE_TYPE is null;
update OFFERS set IS_PUBLIC = 0 where IS_PUBLIC is null;
update OFFERS set APPROVED = 1 where APPROVED is null;
update OFFERS set REPUTATION = 20 where REPUTATION is null and IS_PUBLIC=0;
update OFFERS set REPUTATION = 10 where REPUTATION is null and IS_PUBLIC=1;
