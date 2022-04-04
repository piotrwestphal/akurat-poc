CREATE KEYSPACE IF NOT EXISTS kingboo WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1};
CREATE TABLE IF NOT EXISTS kingboo.profiles (
    profile_id uuid,
    name text,
    role text,
    created_at timestamp,
    updated_at timestamp,
    PRIMARY KEY (profile_id, created_at)
) WITH CLUSTERING ORDER BY (created_at DESC);