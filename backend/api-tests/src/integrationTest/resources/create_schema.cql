CREATE KEYSPACE IF NOT EXISTS data WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1};
CREATE TABLE IF NOT EXISTS data.profiles (
    profile_id uuid,
    name text,
    role text,
    created_at timestamp,
    updated_at timestamp,
    PRIMARY KEY (profile_id, created_at)
) WITH CLUSTERING ORDER BY (created_at DESC);