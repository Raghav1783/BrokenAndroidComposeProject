# Candidate Instructions & Known Bugs (do not change this file name)

This project is intentionally flawed. Your tasks:

1. Fix crashes and freezes
2. Implement navigation to the detail screen
3. Make network calls off the main thread
4. Replace GlobalScope usage with proper ViewModelScope or lifecycle-aware scope
5. Implement local persistence (Room) and use it as cache
6. Correct JSON parsing / DTO mapping
7. Add loading/error/empty states properly
8. Remove memory leak (do not use static Activity references)
9. Implement updateArticle to persist edits
10. Improve project structure where obvious (move business logic out of composables)


Deliverables:
- Working app (no crashes) that loads articles (from the fake API or a real one)
- README describing changes and tradeoffs
- Optional: unit tests and small demo video


Notes:
- Some code intentionally causes the app to freeze or crash. Find and fix them.
- We expect you to explain each bug and your fix in the README.


1- there was no proper setup of room db 
2 - repository funtion was blocking main thread becaue of thread.sleep
we are using suspend function for nonblocking nature using
coroutine on io thread to keep main thread free
3- we were not handling result for error,loading, success state in repository 
4 - we were making object of repository which is bad practice makes it difficult to test and adding dependency injnection
5 - Implemented navigation to the detail screen using article id route
6 - In the mainactiviy there is companion object that is referenced to mainactivity in oncreate, so this will hold the instance of 
    mainactivity even after it gets desroyed and not get garbagecollected, so either we should not store activity reference in
    static variable or  we should handle this in ondestroy of activity.
    To detect leaks we can use profiler and leak canary.
7-  we are using globalscope inside artilescreen, which will not follow lifecycle of activity and will cause memory leak, replaced this with viewmodelscope inside viewmodel
8- and we using dispatcher.main and making api call from there, fixed it by doing on io dispatcher inside viewmodel
9- also the ui is directly calling repository which is breaking clean architecture, so moving this buisness logic to viewmodel, ui should 
just show data.
10 - Added room db for persistence , in repository so now local room will be the source of truth, fetch data is sotred in db and due to flow it 
will be displayed to user, in case of network fail or slow api response cache data will be shown to user.
11 - Added Dto and mapper to entity and domain model following the clean architecture.

12 - used proper ui state that that is lifecycle aware in ui layer for showing data accroding to different state.
 13 - Updating article title is working but since i can't call update article in api so i am currently storing locally the updated values,
so currently i am maintaining a issynced boolean value for all article and when an article is changed its boolean issynced to false, and whenver api calls happen 
and updates the cache, i am ignoring these unsyched data.
But correct logic should be that i should use workmanager and push these unsynched articles in queue and flush them periodically to server and mark them sync and 
everytime api call happens all synched data is deleted and new data is added to local.


Also since api is not providing id for each article , so i was treating "url" and unique ID