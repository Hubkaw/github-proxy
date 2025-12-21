# GitHub Repositories Proxy

Simple Spring Boot proxy to the GitHub API.

## Functionality

Exposes one endpoint:
**GET /{login}**  
Returns a list of non-forked public repositories for the given GitHub login, including:
- Repository name
- Owner login
- For each branch: branch name and last commit SHA

### Success response example (200 OK)
```json
{
  "repositories": [
    {
      "name": "Kuroneko-Botto",
      "ownerLogin": "hubkaw",
      "branches": [
        {
          "name": "master",
          "lastCommitSHA": "7a8f9e8d7c6b5a4f3e2d1c0b9a8f7e6d5c4b3a21"
        },
        {
          "name": "develop",
          "lastCommitSHA": "abc123def456ghi789"
        }
      ]
    }
  ]
}
```
If the user has no non-forked public repositories → returns empty list in the same format (200 OK with "repositories": []).
  
If the GitHub user does not exist:
```json
{
  "status": 404,
  "message": "There is no user named <login>."
}
```
### Tech stack

- Java 25
- Spring Boot 4.0
- WireMock for integration tests

### Configuration  
The application uses GitHub API v3 by default.
To override the base URL set:
```properties
github.base-url=https://api.github.com
```

### Tests
The project includes integration tests using WireMock to stub GitHub API responses  
Tests cover cases:  
- User exists and has non-forked repositories (with branches)
- User exists but has only forked repositories (returns empty list)
- User does not exist (returns 404 with custom message)
