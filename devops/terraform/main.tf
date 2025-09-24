terraform {
  required_version = ">= 1.5.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = var.region
}

resource "aws_ecr_repository" "cft" {
  name = "cft"
  image_tag_mutability = "MUTABLE"
  image_scanning_configuration { scan_on_push = true }
}

resource "aws_db_instance" "cft" {
  identifier = "cft-db"
  allocated_storage = 20
  engine = "postgres"
  engine_version = "16.3"
  instance_class = "db.t3.micro"
  username = var.db_username
  password = var.db_password
  skip_final_snapshot = true
  publicly_accessible = true
}
