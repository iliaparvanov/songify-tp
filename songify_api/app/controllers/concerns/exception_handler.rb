module ExceptionHandler
  extend ActiveSupport::Concern

  # Define custom error subclasses - rescue catches `StandardErrors`
  class AuthenticationError < StandardError; end
  class MissingToken < StandardError; end
  class InvalidToken < StandardError; end

  included do
    # Define custom handlers
    rescue_from ActiveRecord::RecordInvalid, with: :four_twenty_two
    rescue_from ExceptionHandler::AuthenticationError, with: :unauthorized_request
    rescue_from ExceptionHandler::MissingToken, with: :missing_token
    rescue_from ExceptionHandler::InvalidToken, with: :invalid_token

    rescue_from ActiveRecord::RecordNotFound do |e|
      json_response({ message: e.message }, :not_found)
    end
  end

  private

  def four_twenty_two(e)
    json_response({ message: e.message }, 204)
  end

  def missing_token(e)
    json_response({message: e.message}, :MissingToken)
  end

  def invalid_token(e)
    json_response({message: e.message}, :InvalidToken)
  end

  def unauthorized_request(e)
    json_response({message: e.message}, 401)
  end
end