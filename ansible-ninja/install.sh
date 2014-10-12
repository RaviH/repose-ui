#!/bin/bash
set -e

ansible-playbook -i inventory/dev site.yml -vvvv
