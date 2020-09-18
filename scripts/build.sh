cd core 
mvn -q clean install  
cd jms-implementation 
mvn -q clean package 
cd target/interproscan-5-dist 
cd $TRAVIS_BUILD_DIR/core/jms-implementation/target/interproscan-5-dist
df -h  
cd src/coils/ncoils/2.2.1
make
cd ../../../../
cp src/coils/ncoils/2.2.1/ncoils bin/ncoils/2.2.1/
wget ftp://ftp.ebi.ac.uk/pub/databases/interpro/iprscan/5/bin/centos7/rpsbproc.zip
unzip rpsbproc.zip 
cp rpsbproc bin/blast/ncbi-blast-2.9.0+/rpsbproc
chmod +x bin/blast/ncbi-blast-2.9.0+/rpsbproc
if which apt >/dev/null; then
  sudo apt install libdw1 libdw-dev
  #sudo apt-get update
  #sudo apt-get install python3.8
fi
if which yum >/dev/null; then
  #yum install libdw1 libdw-dev
  #yum instal python3.8
fi
python3 -V
echo " checking python version"
./interproscan.sh -i test_proteins.fasta -f tsv -dp -appl sfld, hamap,prints,smart,pfam,pirsf,tigrfam,prositeprofiles,prositepatterns,gene3d,superfamily
./interproscan.sh -i test_proteins.fasta -f tsv -appl sfld, hamap,prints,smart,pfam,pirsf,tigrfam,prositeprofiles,prositepatterns,gene3d,superfamily
./interproscan.sh -i test_nt_seqs.fasta -t n -f tsv -dp -appl sfld, hamap,prints,smart,pfam,pirsf,tigrfam,prositeprofiles,prositepatterns,gene3d,superfamily
./interproscan.sh -i test_nt_seqs.fasta -t n -f tsv -appl sfld, hamap,prints,smart,pfam,pirsf,tigrfam,prositeprofiles,prositepatterns,gene3d,superfamily
./interproscan.sh -i test_nt_seqs.fasta -t n -f tsv,json,xml -appl pfam, pirsf
./interproscan.sh -i test_all_appl.fasta -f tsv -dp -exclappl cdd
./interproscan.sh -i test_all_appl.fasta -f tsv -dp -appl cdd
