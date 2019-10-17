![](https://frontend.cbioportal.org/reactapp/images/369b022222badf37b2b0c284f4ae2284.png)


# Coding Guidelines


**Table of Contents**

- Coding Guidelines
    + [Source Control](#source-control)
    + [Languages](#languages)
    + [Code](#code)
    + [Hosting Environment](#hosting-environment)
    + [Teamwork](#teamwork)
    + [Useful Links](#useful-links)

------

### Source Control
- Do **not** commit directly into **master**!
- Branch your features, then
- Create Pull Requests (PR) when merging into Master
- Do not approve your own PR unless it's a break-the-glass issue
  - If it's an exception, explain why on the comments
- Let a team lead take a look (even if it's a quick review) at your code.
    - Team Leads so far are Daniel (front-end) and Doori (back-end)
- Commits' comments are important. Yes, somebody reads them :)

### Languages
- Backend: Java, Maven framework, etc.
- Front-end: plain JavaScript, any library as needed
- DB: MySQL, any other option if needed
> * We are not using Typescript right now because:
>   * Real-time transpiling can be slow
>   * Not sure about d3 and typescript support into the wild
>   * We could use a framework (react?) but would require some building process
>   * TS has its own learning curve

### Code
- Code cleaning is **important**
- Naming conventions are **importantes** too :)
- Refactoring as a whole is only important once we have a good Proof of concept (POC)
- We will work in multiple js files, referenced from index.html. After the initial POC we will start consolidating.

### Hosting Environment
- Locally for dev. Once closer to a stable point, we can use a VM or similar.
- No CI/CD for now. We can work on this later.

### Teamwork
- Alexander, Isaac, Daniel - Front-End
- Shan, Doori - Back-End
- We will be using Jira as our task dashboard. 
  - See [Jira/Attlassian Dashboard](https://patientsimilarity.atlassian.net/ "Jira/Attlassian Dashboard")
- We will do agile/scrum meeting (~20 min) where we share:
  1) what we did
  2) what we will do
  3) any blockers.
  4) Any questions about requirements or get feedback on your design ideas etc.
  
### Useful Links  
[Project Document](https://docs.google.com/document/d/e/2PACX-1vTdnDpz5UBOF3aPxucO77hl-i7FJagqulRgLS_4y9xcXkWReaoxbfhKtxQ6VZ55wpFysT30mTpPlGNq/pub#h.z11rqsgxo2dh "Project Document") 
```
https://docs.google.com/document/d/e/2PACX-1vTdnDpz5UBOF3aPxucO77hl-i7FJagqulRgLS_4y9xcXkWReaoxbfhKtxQ6VZ55wpFysT30mTpPlGNq/pub#h.z11rqsgxo2dh
```
[cBioPortal](https://www.cbioportal.org/ "cBioPortal") 
```
https://www.cbioportal.org/
```
[cBioPortal Repository](https://github.com/cBioPortal/cbioportal "cBioPortal Repository") 
```
https://www.cbioportal.org/
```
[Team Project Folder](https://drive.google.com/drive/folders/1Vdx99oZNMeFL_ZwkGf-kCJbeqxhlflYG?usp=sharing "Team Folder") 
```
https://drive.google.com/drive/folders/1Vdx99oZNMeFL_ZwkGf-kCJbeqxhlflYG?usp=sharing
```
    
    
    
