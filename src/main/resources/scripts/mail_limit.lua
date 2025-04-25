local totalKey = KEYS[1]
local clubKey = KEYS[2]
local totalLimit = tonumber (ARGV[1])
local clubLimit = tonumber (ARGV[2])
local request = tonumber (ARGV[3])
local expireAt = tonumber(ARGV[4])

if redis.call('EXISTS', totalKey) == 0 then
    redis.call('SET', totalKey, totalLimit)
    redis.call('EXPIREAT', totalKey, expireAt)
end

if redis.call('EXISTS', clubKey) == 0 then
    redis.call('SET', clubKey, clubLimit)
    redis.call('EXPIREAT', clubKey, expireAt)
end

local totalCurrent = tonumber(redis.call('GET', totalKey) or '0')
local clubCurrent = tonumber(redis.call('GET', clubKey) or '0')

if (totalCurrent < request) or (clubCurrent < request) then
    return false;
else
    redis.call('DECRBY', totalKey, request)
    redis.call('DECRBY', clubKey, request)
    return true;
end
