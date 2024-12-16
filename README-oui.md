Quand vous devez commit : 

git pull https://github.com/Feudai/PAT_Encheres.git
git add .
git commit -m "votre message"
git push -origin [Paul/Antoine/Tim] *En fonction de qui vous êtes*

Une fois qu'on est sûrs que les commit de tout le monde sont faits :

*S'assurer que la/les modifs sont sur différentes lignes sur les deux fichiers à merge ensembles, sinon il y aura conflit* 

git checkout master *Pour changer de branche*
git merge *branche de l'autre*

git config --add push.autoSetupRemote true
