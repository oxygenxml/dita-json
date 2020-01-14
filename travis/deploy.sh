git config user.name "radu_coravu";
git config user.email "radu_coravu@sync.ro";
git fetch;
git checkout -f master;
git reset;
git commit -m "New release - ${TRAVIS_TAG}";
git push origin HEAD:master; 