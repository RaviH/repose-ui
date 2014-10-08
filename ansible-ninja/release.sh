#!/bin/bash
set -e

USAGE="`basename $0` [-e]<environment> [-l]<limit>"
limit=''

while getopts "hv:e:l:" OPTION; do
    case $OPTION in
        h)
        echo $USAGE
        exit 0
        ;;
        e)
        environment=${OPTARG}
        ;;
        l)
        [ -n ${OPTARG} ] && limit="--limit ${OPTARG}"
        ;;
    esac
done

ansible-playbook -i inventory/$environment site.yml -vvvv $limit
