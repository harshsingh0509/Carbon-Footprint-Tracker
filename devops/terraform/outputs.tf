output "ecr_repository_url" {
  value = aws_ecr_repository.cft.repository_url
}
output "rds_endpoint" {
  value = aws_db_instance.cft.address
}
