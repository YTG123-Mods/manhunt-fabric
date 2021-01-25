### *This repo will be removed if Dream requests so*
# Manhunt Fabric
A mod which implements a lot of the mechanics found in Dream's (https://youtube.com/user/DreamTraps) Minecraft Manhunt plugin.

[
![Downloads](https://img.shields.io/badge/dynamic/json?color=5da545&label=Download From Modrinth&labelColor=cecece&query=downloads&url=https%3A%2F%2Fapi.modrinth.com%2Fapi%2Fv1%2Fmod%2Fmanhunt&logo=data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxNCIgdmlld0JveD0iMCAwIDUxMiA1MTQiIGZpbGw9Im5vbmUiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CiAgPHBhdGggZmlsbC1ydWxlPSJldmVub2RkIiBjbGlwLXJ1bGU9ImV2ZW5vZGQiIGQ9Ik01MDMuMTYgMzIzLjU2QzUxNC41NSAyODEuNDcgNTE1LjMyIDIzNS45MSA1MDMuMiAxOTAuNzZDNDY2LjU3IDU0LjIyOTkgMzI2LjA0IC0yNi44MDAxIDE4OS4zMyA5Ljc3OTkxQzgzLjgxMDEgMzguMDE5OSAxMS4zODk5IDEyOC4wNyAwLjY4OTk0MSAyMzAuNDdINDMuOTlDNTQuMjkgMTQ3LjMzIDExMy43NCA3NC43Mjk4IDE5OS43NSA1MS43MDk4QzMwNi4wNSAyMy4yNTk4IDQxNS4xMyA4MC42Njk5IDQ1My4xNyAxODEuMzhMNDExLjAzIDE5Mi42NUMzOTEuNjQgMTQ1LjggMzUyLjU3IDExMS40NSAzMDYuMyA5Ni44MTk4TDI5OC41NiAxNDAuNjZDMzM1LjA5IDE1NC4xMyAzNjQuNzIgMTg0LjUgMzc1LjU2IDIyNC45MUMzOTEuMzYgMjgzLjggMzYxLjk0IDM0NC4xNCAzMDguNTYgMzY5LjE3TDMyMC4wOSA0MTIuMTZDMzkwLjI1IDM4My4yMSA0MzIuNCAzMTAuMyA0MjIuNDMgMjM1LjE0TDQ2NC40MSAyMjMuOTFDNDY4LjkxIDI1Mi42MiA0NjcuMzUgMjgxLjE2IDQ2MC41NSAzMDguMDdMNTAzLjE2IDMyMy41NloiIGZpbGw9IiM1REE0MjYiLz4KICA8cGF0aCBkPSJNMzIxLjk5IDUwNC4yMkMxODUuMjcgNTQwLjggNDQuNzUwMSA0NTkuNzcgOC4xMTAxMSAzMjMuMjRDMy44NDAxMSAzMDcuMzEgMS4xNyAyOTEuMzMgMCAyNzUuNDZINDMuMjdDNDQuMzYgMjg3LjM3IDQ2LjQ2OTkgMjk5LjM1IDQ5LjY3OTkgMzExLjI5QzUzLjAzOTkgMzIzLjggNTcuNDUgMzM1Ljc1IDYyLjc5IDM0Ny4wN0wxMDEuMzggMzIzLjkyQzk4LjEyOTkgMzE2LjQyIDk1LjM5IDMwOC42IDkzLjIxIDMwMC40N0M2OS4xNyAyMTAuODcgMTIyLjQxIDExOC43NyAyMTIuMTMgOTQuNzYwMUMyMjkuMTMgOTAuMjEwMSAyNDYuMjMgODguNDQwMSAyNjIuOTMgODkuMTUwMUwyNTUuMTkgMTMzQzI0NC43MyAxMzMuMDUgMjM0LjExIDEzNC40MiAyMjMuNTMgMTM3LjI1QzE1Ny4zMSAxNTQuOTggMTE4LjAxIDIyMi45NSAxMzUuNzUgMjg5LjA5QzEzNi44NSAyOTMuMTYgMTM4LjEzIDI5Ny4xMyAxMzkuNTkgMzAwLjk5TDE4OC45NCAyNzEuMzhMMTc0LjA3IDIzMS45NUwyMjAuNjcgMTg0LjA4TDI3OS41NyAxNzEuMzlMMjk2LjYyIDE5Mi4zOEwyNjkuNDcgMjE5Ljg4TDI0NS43OSAyMjcuMzNMMjI4Ljg3IDI0NC43MkwyMzcuMTYgMjY3Ljc5QzIzNy4xNiAyNjcuNzkgMjUzLjk1IDI4NS42MyAyNTMuOTggMjg1LjY0TDI3Ny43IDI3OS4zM0wyOTQuNTggMjYwLjc5TDMzMS40NCAyNDkuMTJMMzQyLjQyIDI3My44MkwzMDQuMzkgMzIwLjQ1TDI0MC42NiAzNDAuNjNMMjEyLjA4IDMwOC44MUwxNjIuMjYgMzM4LjdDMTg3LjggMzY3Ljc4IDIyNi4yIDM4My45MyAyNjYuMDEgMzgwLjU2TDI3Ny41NCA0MjMuNTVDMjE4LjEzIDQzMS40MSAxNjAuMSA0MDYuODIgMTI0LjA1IDM2MS42NEw4NS42Mzk5IDM4NC42OEMxMzYuMjUgNDUxLjE3IDIyMy44NCA0ODQuMTEgMzA5LjYxIDQ2MS4xNkMzNzEuMzUgNDQ0LjY0IDQxOS40IDQwMi41NiA0NDUuNDIgMzQ5LjM4TDQ4OC4wNiAzNjQuODhDNDU3LjE3IDQzMS4xNiAzOTguMjIgNDgzLjgyIDMyMS45OSA1MDQuMjJaIiBmaWxsPSIjNURBNDI2Ii8+Cjwvc3ZnPgo=)
](https://modrinth.com/mod/manhunt)

[KDoc (Javadoc in disguise)](https://ytg123-mods.github.io/manhunt-fabric/-modules.html)

***Contributions are welcome!***

## Extra Features
It's all managed using Minecraft commands.
This mod adds two new commands: `/speedrunner` and `/hunters`.

## How it works:
If you're not familiar with Dream's Minecraft Manhunt series on Youtube, you should check that out.
* You can set the speedrunner by using `/speedrunner set <target>`. That sets that specific Player to be the speedrunner.
* You can view who the speedrunner is by using `/speedrunner get`.
* You can unset the speedrunner by using `/speedrunner clear`.

<br/>

* You can add hunters by using `/hunters add <target>`.
* You can clear the hunter list by using `/hunters clear`.
* You can get the hunter list by using `/hunters get`.

### Differences:
* This mod uses the lodestone compass mechanic introduced in Minecraft 1.16.
* This mod is configurable! Yes! You can view the comments in the config itself to understand it.

This mod also adds a Cloth Config screen you can open using Mod Menu (client-side only, of course).

## Planned Features
* Freezing - Freezes hunters when speedrunner looks at them
* Actual win/loss system
