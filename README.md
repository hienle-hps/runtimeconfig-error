# runtimeconfig-error
https://issuetracker.google.com/issues/35902219

Use CLI to create and verify variable:
```
gcloud beta runtime-config configs create my_config
gcloud beta runtime-config configs variables set my_var my_value --config-name=my_config --is-text
gcloud beta runtime-config configs variables get-value my_var --config-name=my_config
```

Compare results of calling local (`appengineRun`) vs deployed (`appengineDeploy`).
